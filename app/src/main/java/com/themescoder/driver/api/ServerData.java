package com.themescoder.driver.api;

import com.themescoder.driver.models.DriverDetails;
import com.themescoder.driver.models.MenufactureDetails;
import com.themescoder.driver.models.OrdersResponse;
import com.themescoder.driver.models.SettingsData;

import java.util.List;

public class ServerData {

    public static SettingsData settingsData;

    public static DriverDetails currentDriver;
    public static OrdersResponse ordersResponse;

    public static List<MenufactureDetails> dashboardObjectsList;
    public static List<MenufactureDetails> historyObjectsList;

    public static String MAPTYPE = "internal";      // external / internal
}
