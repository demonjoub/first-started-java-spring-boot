package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String item;
  private float amount;

  protected Expense() {}

  public Expense(String item, float amount) {
    this.item = item;
    this.amount = amount;
  }

  public String getItem() {
    return this.item;
  }

  public float getAmount() {
    return this.amount;
  }

  public Long getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return id + ". " + item + " - " + amount + " USD";
  }

  public void setId(Long mid) {
    this.id = mid;
  }
}