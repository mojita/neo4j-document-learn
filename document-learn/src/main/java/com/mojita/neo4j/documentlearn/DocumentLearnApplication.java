package com.mojita.neo4j.documentlearn;

import org.neo4j.graphdb.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import static com.mojita.neo4j.documentlearn.utlis.Neo4jUtlis.loadDatabaseByJavaConf;

@SpringBootApplication
public class DocumentLearnApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DocumentLearnApplication.class, args);
        String databaseDir = "C:\\graph2.db";
        createSimpleGraph(databaseDir);
    }


    /**
     * 创建两个人的关系
     * @param databaseDir
     */
    public static void createSimpleGraph(String databaseDir) {
        GraphDatabaseService graphService = loadDatabaseByJavaConf(databaseDir);
        try(Transaction tx = graphService.beginTx()) {
            //TODO 通过这种方式创建节点和关系必须放在事物中
            Node firstNode = graphService.createNode(Label.label("人"));
            firstNode.setProperty("姓名", "张三");
            Node secondryNode = graphService.createNode(Label.label("人"));
            secondryNode.setProperty("姓名", "王五");
            Relationship relationship = firstNode.createRelationshipTo(secondryNode, RelationshipType.withName("兄弟"));
            relationship.setProperty("开始时间", "2018-10-01");
            tx.success();
        }
    }







}
