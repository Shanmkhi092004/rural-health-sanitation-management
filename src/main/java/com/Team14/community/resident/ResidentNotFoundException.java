
package com.Team14.community.resident;

public class ResidentNotFoundException extends RuntimeException {

    public ResidentNotFoundException(Long id) {
        super("Resident not found with ID: " + id);
    }
}
