package com.dnu.ffeks.lab1;

import java.io.InputStream;
import java.util.List;

public interface SmallestRowFounder {

    String getTheSmallestRow(InputStream in);

    String getTheSmallestRowOfList(List<String> rows);
}