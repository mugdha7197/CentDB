package com.csci5408.centdb.persistence.impl;

import com.csci5408.centdb.persistence.IFileReader;
import com.csci5408.centdb.persistence.IQueryDao;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class QueryDaoTest {

    @Test
    public void test() throws IOException {
        IFileReader fileReader = new FileReader();
        fileReader.getMetadata();
    }
}
