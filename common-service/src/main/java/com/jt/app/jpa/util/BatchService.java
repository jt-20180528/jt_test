package com.jt.app.jpa.util;

import java.util.List;

public interface BatchService<T> {

    public Integer batchInsert(List<T> list, Integer batchSize);

    public Integer batchUpdate(List<T> list, Integer batchSize);
}
