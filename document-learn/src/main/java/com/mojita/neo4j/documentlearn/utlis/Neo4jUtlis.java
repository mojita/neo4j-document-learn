package com.mojita.neo4j.documentlearn.utlis;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import java.io.File;

/**
 * created by mojita on 2019/3/6
 */
public class Neo4jUtlis {


    /**
     * 通过java进行配置加载数据库
     * @param databaseDir
     * @return
     */
    public static GraphDatabaseService loadDatabaseByJavaConf(String databaseDir) {
        GraphDatabaseService graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(databaseDir))
                .setConfig( GraphDatabaseSettings.pagecache_memory, "512M" )
                .setConfig( GraphDatabaseSettings.string_block_size, "60" )
                .setConfig( GraphDatabaseSettings.array_block_size, "300" )
                .newGraphDatabase();
        return graphDb;
    }

    /**
     * 通过加载配置文件的方式加载数据库
     * @param databaseDir        数据库路径
     * @param confPath      配置文件路径
     * @return
     */
    private static GraphDatabaseService loadNeo4jDatabaseByConfFile(String databaseDir,String confPath) {
        GraphDatabaseService graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(databaseDir))
                .loadPropertiesFromFile(confPath)
                .newGraphDatabase();
        return graphDb;
    }

    public static void loadNeo4jDatabaseOnlyRead(String databaseDir) {
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(new File(databaseDir))
                .setConfig( GraphDatabaseSettings.read_only, "true" )
                .newGraphDatabase();
    }

    /**
     * 注册钩子函数，当JVM进程结束或者Ctrl-C 时关闭数据库
     * @param graphDb
     */
    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread(graphDb::shutdown));
    }

}
