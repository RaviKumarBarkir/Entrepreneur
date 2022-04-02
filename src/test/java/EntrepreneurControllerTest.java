import com.privado.controller.EntrepreneurController;
import com.privado.model.Candidate;
import com.privado.services.EntrepreneurServices;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration
public class EntrepreneurControllerTest {

    @InjectMocks
    EntrepreneurController entrepreneurController;
    
    @Mock
    EntrepreneurServices entrepreneurServices;

    /*@org.junit.jupiter.api.Test
    void contextLoads() {
    }
*/
    @Test(expected = NullPointerException.class)
    public void testcreate() {
        Candidate[] request=new Candidate[1];
        Given:
        request[0]=new Candidate();
        ResponseEntity<? extends Object>  ResponseEntity = null;
        When:
        Mockito.when(entrepreneurServices.crateGroups(request)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        then:
        Mockito.verify(entrepreneurController.crate(request));
    }

}
