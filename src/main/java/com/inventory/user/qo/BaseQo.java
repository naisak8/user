package com.inventory.user.qo;

import com.inventory.user.exceptions.enums.OrderBy;
import lombok.Data;

import java.util.Map;

@Data
public class BaseQo {
    private int pageSize = 25;
    private int pageNumber = 0;
    private Map<String, OrderBy> sortBy;
}
