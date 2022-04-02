package com.privado.services;

import com.privado.entities.Group;
import com.privado.model.Candidate;
import com.privado.model.Role;
import com.privado.repository.GroupRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntrepreneurServices {

    private static final Logger log = LogManager.getLogger(EntrepreneurServices.class);

    @Autowired
    private GroupRepository groupRepository;

    public ResponseEntity<? extends Object> crateGroups(Candidate[] candidates){
        log.info("Start of crateGroups "+candidates.toString());
        try {
            long number_of_sales_person = 0;
            long number_of_marketing_person = 0;
            long number_of_engineering_person = 0;
            long number_of_person_age_grater = 0;
            List<Candidate> nongroup = new ArrayList<>();
            List<Candidate> group = new ArrayList<>();
            Map<String, List<Candidate>> responce = new HashMap<String, List<Candidate>>();
            Map<String, List<Candidate>> groupLocation =
                    Arrays.stream(candidates).collect(Collectors.groupingBy(Candidate::getLocation));

            for (Map.Entry<String, List<Candidate>> entry : groupLocation.entrySet()) {
                log.info("Key" + entry.getKey() + "the value is " + entry.getValue());
                number_of_sales_person = entry.getValue().stream().filter(candidate -> candidate.getRole().equals(Role.Sales)).count();
                number_of_marketing_person = entry.getValue().stream().filter(candidate -> candidate.getRole().equals(Role.Marketing)).count();
                number_of_engineering_person = entry.getValue().stream().filter(candidate -> candidate.getRole().equals(Role.Engineer)).count();
                number_of_person_age_grater = entry.getValue().stream().filter(candidate -> candidate.getAge() > 35).count();
                if (number_of_sales_person == number_of_marketing_person && number_of_sales_person == number_of_engineering_person && number_of_person_age_grater >= 1) {
                    log.info("Successful journey  " + entry.getValue() + group.addAll(entry.getValue()));
                } else {
                    log.info("un_Successful journey  " + entry.getValue());
                    Optional<Candidate> outcast;
                    if (number_of_sales_person == number_of_marketing_person && number_of_person_age_grater >= 1 && number_of_engineering_person > number_of_marketing_person) {
                        while (number_of_marketing_person != number_of_engineering_person) {
                            outcast = entry.getValue().stream().filter(candidate -> candidate.getRole().equals(Role.Engineer)).findFirst();
                            if (outcast.isPresent()) {
                                nongroup.add(outcast.get());
                                entry.getValue().remove(outcast.get());
                                number_of_marketing_person = number_of_marketing_person - 1;
                            }
                        }
                    } else if (number_of_engineering_person == number_of_marketing_person && number_of_person_age_grater >= 1 && number_of_sales_person > number_of_marketing_person) {
                        while (number_of_sales_person != number_of_engineering_person) {
                            outcast = entry.getValue().stream().filter(candidate -> candidate.getRole().equals(Role.Sales)).findFirst();
                            if (outcast.isPresent()) {
                                nongroup.add(outcast.get());
                                entry.getValue().remove(outcast.get());
                                number_of_sales_person = number_of_sales_person - 1;
                            }
                        }
                    } else if (number_of_engineering_person == number_of_marketing_person && number_of_person_age_grater >= 1 && number_of_marketing_person > number_of_sales_person) {
                        while (number_of_marketing_person != number_of_engineering_person) {

                            outcast = entry.getValue().stream().filter(candidate -> candidate.getRole().equals(Role.Marketing)).findFirst();
                            if (outcast.isPresent()) {
                                nongroup.add(outcast.get());
                                entry.getValue().remove(outcast.get());
                                number_of_marketing_person = number_of_marketing_person - 1;
                            }
                        }
                    }
                    group.addAll(entry.getValue());
                }
            }
            log.info("GRoup by place proper  " + group);
            log.info("non GRoup   " + nongroup);
            responce.put("groups", group);
            responce.put("no-groups", nongroup);
            StringBuilder b = new StringBuilder();
            group.forEach(b::append);
            groupRepository.save(new Group("groups", b.toString()));
            b = new StringBuilder();
            nongroup.forEach(b::append);
            groupRepository.save(new Group("no-groups", b.toString()));
            log.info("End of crateGroups ");
            return new ResponseEntity<>(responce, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
