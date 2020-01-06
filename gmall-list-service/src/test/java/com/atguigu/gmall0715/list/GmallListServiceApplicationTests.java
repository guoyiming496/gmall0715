package com.atguigu.gmall0715.list;


import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
 public class GmallListServiceApplicationTests {
    //操作es
    @Autowired
    private JestClient jestClient;
    @Test
    public  void contextLoads() {
    }

    @Test
    public void testES() throws IOException {
        // 执行命令：
		/*
		1.	定义dsl 语句
		GET /movie_chn/movie/_search { }
		2.	确定动作，准备执行
		3.	获取返回结果
		 */
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"actorList.name\": \"张译\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        // 查询
        Search search = new Search.Builder(query).addIndex("movie_chn").addType("movie_type_chn").build();
        // 执行
        SearchResult searchResult = jestClient.execute(search);
        // 获取到执行的结果
        List<SearchResult.Hit<Map, Void>> hits = searchResult.getHits(Map.class);

        for (SearchResult.Hit<Map, Void> hit : hits) {
            Map map = hit.source;
            System.out.println(map.get("name"));
        }
    }

}
