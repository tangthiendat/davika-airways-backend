package com.dvk.ct250backend.infrastructure.utils;

import com.dvk.ct250backend.app.dto.request.SearchCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RequestParamUtils {

    public List<Sort.Order> toSortOrders(Map<String, String> params) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        String sortBy = params.getOrDefault("sortBy", "createdAt");
        String direction = params.getOrDefault("direction", "desc");
        if(sortBy != null && !sortBy.isEmpty()) {
            Sort.Order createdAtOrder = new Sort.Order(Sort.Direction.fromString(direction), sortBy);
            Sort.Order updatedAtOrder = new Sort.Order(Sort.Direction.fromString("asc"), "updatedAt");
            sortOrders.addAll(List.of(createdAtOrder, updatedAtOrder));
        }
        return sortOrders;
    }

    public List<Sort.Order> toSortOrdersByElastic(Map<String, String> params) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        String sortBy = params.getOrDefault("sort", "created_at");
        String direction = params.getOrDefault("order", "desc");
        if(sortBy != null && !sortBy.isEmpty()) {
            Sort.Order order = new Sort.Order(Sort.Direction.fromString(direction), sortBy);
            sortOrders.add(order);
        }
        return sortOrders;
    }

    public List<SearchCriteria> getSearchCriteria(Map<String, String> params, String key) {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        String value = params.get(key);
        if(value != null && !value.isEmpty()) {
            String[] valueArr = value.split(",");
            for (String searchValue : valueArr) {
                SearchCriteria searchCriteria = SearchCriteria.builder()
                        .key(key)
                        .operation("=")
                        .value(searchValue)
                        .build();
                searchCriteriaList.add(searchCriteria);
            }
        }
        return searchCriteriaList;
    }
}

