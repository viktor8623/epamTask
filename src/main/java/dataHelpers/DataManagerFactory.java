package dataHelpers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DataManagerFactory {

    private static final Map<DataStorageType, Supplier<DataManager>> dataManagerMap = new HashMap<>();

    private static final Supplier<DataManager> xmlParserSupplier = XMLParser::new;

    private static final Supplier<DataManager> csvParserSupplier = CSVParser::new;

    private static final Supplier<DataManager> dbHelperSupplier = DBHelper::new;

    static{
        dataManagerMap.put(DataStorageType.XML, xmlParserSupplier);
        dataManagerMap.put(DataStorageType.CSV, csvParserSupplier);
        dataManagerMap.put(DataStorageType.DB, dbHelperSupplier);
    }

    public static DataManager getDataManager(DataStorageType type){
        return dataManagerMap.get(type).get();
    }
}
