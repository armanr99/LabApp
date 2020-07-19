package main.java.models.Storage;


import main.java.models.General.Entity;

import java.io.InvalidObjectException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public abstract class Repository<T extends Entity> {
    private HashMap<Integer, T> records;
    private int nextId;

    private Repository() {
        records = new HashMap<>();
        nextId = 0;
    }

    public T find(int id) {
        if (records.containsKey(id)) {
            return records.get(id);
        } else {
            throw new NoSuchElementException("No entity with given id was found");
        }
    }

    private int getNextId() {
        return nextId++;
    }

    public void insert(T newRecord) throws InvalidObjectException {
        for (T record : records.values()) {
            if (record.equals(newRecord)) {
                throw new InvalidObjectException("Duplicate element insert detected");
            }
        }
        int recordId = getNextId();
        newRecord.setId(recordId);
        records.put(recordId, newRecord);
    }
}