package com.wczaja.apidemo.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wczaja.apidemo.resources.UniqueWordResource;
import com.wczaja.apidemo.services.UniqueWordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UniqueWordServiceTest {

    @Autowired
    UniqueWordService uniqueWordService;

    @Test
    public void testGetUniqueWordsFromParagraph() throws JsonProcessingException {
        String paragraph = "One Two Three Four Five Five Three";
        List<UniqueWordResource> expectedUniqueWordResources = new ArrayList<>();
        expectedUniqueWordResources.add(new UniqueWordResource("five", 2));
        expectedUniqueWordResources.add(new UniqueWordResource("four", 1));
        expectedUniqueWordResources.add(new UniqueWordResource("one", 1));
        expectedUniqueWordResources.add(new UniqueWordResource("three", 2));
        expectedUniqueWordResources.add(new UniqueWordResource("two", 1));

        List<UniqueWordResource> uniqueWordResources = uniqueWordService.getUniqueWordsFromParagraph(paragraph);

        ObjectMapper mapper = new ObjectMapper();

        assertThat(mapper.writeValueAsString(uniqueWordResources))
                .isEqualTo(mapper.writeValueAsString(expectedUniqueWordResources));
    }

    @Test
    public void testGetUniqueWordsFromParagraph_EmptyParagraph() {
        String paragraph = "";

        List<UniqueWordResource> uniqueWordResources = uniqueWordService.getUniqueWordsFromParagraph(paragraph);

        assertThat(uniqueWordResources).isEqualTo(new ArrayList<>());
    }
}
