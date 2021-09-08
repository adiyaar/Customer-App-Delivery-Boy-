package com.themescoder.androidstore.enums;


/**
 * Created by Rahul Hooda on 14/7/17.
 */
public enum AppEnvironment {

    SANDBOX {
        @Override
        public String merchant_Key() {
            return "3IarLyil";
        }

        @Override
        public String merchant_ID() {
            return "6750071";
        }

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "brHab0hOBT";
        }

        @Override
        public boolean debug() {
            return true;
        }
    },
    PRODUCTION {
        @Override
        public String merchant_Key() {
            return "QylhKRVd";
        }
        @Override
        public String merchant_ID() {
            return "5960507";
        }
        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "seVTUgzrgE";
        }

        @Override
        public boolean debug() {
            return false;
        }
    };

    public abstract String merchant_Key();

    public abstract String merchant_ID();

    public abstract String furl();

    public abstract String surl();

    public abstract String salt();

    public abstract boolean debug();


}
