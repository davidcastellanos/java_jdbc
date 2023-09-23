package org.davidcastellanos.cursospringjdbc.jdbcTemplate.lotesBatchOBulk.models;

public class Address {
    private Integer id;
    private String street;
    private Integer stNumber;
    private String pc;
    private Integer employeeId;

    public Address() {
    }

    public Address(String street, Integer stNumber, String pc, Integer employeeId) {
        this.street = street;
        this.stNumber = stNumber;
        this.pc = pc;
        this.employeeId = employeeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStNumber() {
        return stNumber;
    }

    public void setStNumber(Integer stNumber) {
        this.stNumber = stNumber;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
