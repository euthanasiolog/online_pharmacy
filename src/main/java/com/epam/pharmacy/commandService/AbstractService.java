package com.epam.pharmacy.commandService;

import com.epam.pharmacy.command.RequestContent;
import com.epam.pharmacy.entity.Entity;

public interface AbstractService {
    void create(RequestContent requestContent);
    Entity findById(int id);
    void update(RequestContent requestContent);
    void delete(RequestContent requestContent);
}
