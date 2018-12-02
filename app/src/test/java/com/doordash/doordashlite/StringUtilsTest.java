package com.doordash.doordashlite;

import com.doordash.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {
    @Test
    public void generateTags() {
        List<String> input = new ArrayList<>();
        input.add("Chinese");
        input.add("Soup");
        assertEquals(StringUtils.getTagString(input), "Chinese, Soup");
    }

    @Test
    public void generateTags_Empty() {
        List<String> input = Collections.emptyList();
        assertEquals(StringUtils.getTagString(input), "");
    }
}
