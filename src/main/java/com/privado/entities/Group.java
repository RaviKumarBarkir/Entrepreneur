package com.privado.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "group")
public class Group {
  @Id
  private String batchId;
  private String  candidates;

  public Group() {

  }

  public Group(String batchId, String  candidates) {
    this.batchId = batchId;
    this.candidates = candidates;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String  getCandidates() {
    return candidates;
  }

  public void setCandidates(String  candidates) {
    this.candidates = candidates;
  }
}
