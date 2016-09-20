
package com.zfgt.common.lianlian.pay.utils;

public class EnvConstants {
    private EnvConstants() {
    }

    /**
     * TODO 商户号，商户MD5 key 配置。本测试Demo里的“PARTNER”；强烈建议将私钥配置到服务器上，以免泄露。“MD5_KEY”字段均为测试字段。正式接入需要填写商户自己的字段
     */
    public static final String PARTNER_PREAUTH = "201504071000272504"; // 短信

    public static final String MD5_KEY_PREAUTH = "201504071000272504_test_20150417";
    //测试商户
   //public static final String PARTNER = "201408071000001543";
   //真实商户 
    public static final String PARTNER = "201506011000349506";
    //测试秘钥
    //public static final String MD5_KEY = "201408071000001543test_20140812";
    //真实秘钥
   public static final String MD5_KEY = "www.baiyimao.com";
    
    public static final String QUERY_BANKCARD_URL = "https://yintong.com.cn/traderapi/bankcardquery.htm"; //银行卡卡bin信息查询
    public static final String QUERY_USERBANKCARD_URL = "https://yintong.com.cn/queryapi/bankcardbindlist.htm"; //签约卡查询
    public static final String QUERY_CNAPSCODE_URL = "https://yintong.com.cn/traderapi/CNAPSCodeQuery.htm"; //大额行号查询
  //商户支付结果查询服务接口
    public static final String ORDER_QUERY_URL="https://yintong.com.cn/traderapi/orderquery.htm";
    //代付接口
    public final static String CASH_PAY = "https://yintong.com.cn/traderapi/cardandpay.htm";
    //web认证支付接口
    public static final String AUTH_PAY_URL="https://cashier.lianlianpay.com/payment/authpay.htm";
    // 商户（RSA）私钥 TODO 强烈建议将私钥配置到服务器上，否则有安全隐患
//     public static final String RSA_PRIVATE =
//     "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
    public static final String RSA_PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ3SnDmjhGUKpFzsKfSOyHKHJOsDSN7SI8DXYgV+BfPPwpwYS59gQZdUSKBAns9T+OMFcZcibWTUc2zykU9dfTKXg26KQus9Gcxluq9/uI0JqRHJZm1xQzl7hcf736RX644kQexbirY5UWS8qZ1FNhsQJaDul/RMZJdb715IEib5AgMBAAECgYBE74ORoZzozZcxh25kBiID9Ifpp0ujGELJrsp//6X18x/cMriFavP7d7MpXFCB2vnxn6UYEM+bpTits95Ivls6mEWKaYpZxgBdH9Q2G9Oj4VS/bWkT2CRiX2tI2u8wVuYsoMusYDNZbTFOtPI+dNYTaY3IEEBz7rdcVRJNqH6SIQJBAM/i84lmGZxnd5kysmbuIy70DrfqpZ5xajEzpbp8I6FZEnpd+ukqo/ZLWXHI+t4Gme5NMmnwwVi9e01JxbR29JUCQQDCWW6Lj3f/prss/tcyR73/nio8ozUmE3tfKTDQAozWx64Ff7x4VW6+oEZLrICkkKPjQJEFCHXn6HkT3SSUbUvVAkEAss7wzrPFP6Q3WMwkayhVluNRbZwkjF3dCyjzWa3BL9zfaeVAFiIZGZXB/37xWNuwtyorPLhadau1TMsxhIH06QJALL5KJPcyuTi5fvJsuLarl13ie3NXmRyDGrVw93Ke2hO+jKWLzmhcxqY/iMx+EWT7ViZXPMFl464xsdEHyuQewQJBAKJU3VgMHAdzAerY4tRH1l93vgOn1zNiObieIplAdjCcmsdUYVSy5ilmHxnH38uidZLwFPLcxaR7sZZDMpdrl0w=";
    // 银通支付（RSA）公钥
    public static final String RSA_YT_PUBLIC =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
    
}
