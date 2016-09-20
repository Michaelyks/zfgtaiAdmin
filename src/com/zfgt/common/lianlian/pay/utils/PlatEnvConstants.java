
package com.zfgt.common.lianlian.pay.utils;

public class PlatEnvConstants {
    private PlatEnvConstants() {
    }

    /**
     * TODO 商户号，商户MD5 key 配置。本测试Demo里的“PARTNER”；强烈建议将私钥配置到服务器上，以免泄露。“MD5_KEY”字段均为测试字段。正式接入需要填写商户自己的字段
     */

    public static final String PARTNER = "201506011000349506";
 
    public static final String MD5_KEY = "www.baiyimao.com";
    
    public static final String QUERY_BANKCARD_URL = "https://yintong.com.cn/traderapi/bankcardquery.htm"; //银行卡卡bin信息查询
    public static final String QUERY_USERBANKCARD_URL = "https://yintong.com.cn/queryapi/bankcardbindlist.htm"; //签约卡查询
    //代付接口
    public final static String CASH_PAY = "https://yintong.com.cn/traderapi/cardandpay.htm";
    // 商户（RSA）私钥 TODO 强烈建议将私钥配置到服务器上，否则有安全隐患
    // public static final String RSA_PRIVATE =
    // "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
    public static final String RSA_PRIVATE ="MIICXAIBAAKBgQDvZWGomYUfjrjLgJ4w8JqaMc/mN9vdCVVc29VmL52z7J94QJxlbRucjYRnkQTqFYLZAJQ7FOFyFvwxCbZUQzHHNLRMewxZvLoL+MnuK3xqJE733EndNK0t1b7n4kLC1g6qeOKkQNdeswvPinCOR6EMlA0xqYZyMKeF0AJPNWr1ZwIDAQABAoGAZs1DbbJXT2DrjEA0LlH0IgkH/RW5raanaJPihNonv676IBqmzotXExhWZl8JHDiWkvdDsEdcNLEYJTrFeFPOmKc0NXVySfMkiKvKhGt9aUFHRnVyrPuQNwUpqsx6jPPqNftitWaYVK9L9xqB+RcHBVYMgDkJK5MdqUGqt8f0/MECQQD33yIRWBTqvUEeWB4Cu2Hhz1Ce1c0Tjda9+HtXitCrFTeMEeXcb89DqXpzQmbxibfu5GOJN4T2kjkoEFyXo6PhAkEA9z8YqO0sHEqkHiy3Bpk84qB5uWP+rikKbx8fk24BqfVrj4aJh78Z/FqtvWFCygq86E99XQztPhMKRtBgv9jCRwJBAJzQ6Aa9CQ6xiR8fdG0aC4UG2eSDMukqFS++opnx0q5KEAhn0lqxyhjuge+llOc+z8UtbOOE4nP0coCsSUgOWUECQFOPUoE87sx8+WxtRkoHngklnR3nypoHqswVY7GK2ysak4iFOpPTJTAhYViAt3bbHC8TNlYtWTzCaJW4NQqBcl8CQB71q6a+AjdOr0ofyMWODuLowQ6oCMnKU0Xdbjj/vOgAl33ml1rKIuXmBNcZWX7N0nQfhu1RxjkGLLFUMYdHPS8=";
    // 银通支付（RSA）公钥
    public static final String RSA_YT_PUBLIC =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
    
}
