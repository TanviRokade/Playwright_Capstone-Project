package TestNGSauceDemoWithPlayWright;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestNGAddtoCart {
    Playwright obj_playwright;
    Browser obj_browser;
    BrowserContext obj_context;
    Page obj_Page;

    static String username = "standard_user";
    static String password ="secret_sauce";
    static String css_loginButton="input[type='submit'][value='Login']";
    static String id_username ="#user-name";
    static String id_password = "#password";
    static String tshirt = "#add-to-cart-sauce-labs-bolt-t-shirt";
    static String backpack ="#add-to-cart-sauce-labs-backpack";
    static String fleeceJacket = "#add-to-cart-sauce-labs-fleece-jacket";
    static String cartBtn = "#shopping_cart_container";
    static String cartContainer = "#cart_contents_container";

    @BeforeMethod
    public void setUp(){
        obj_playwright = Playwright.create();
        obj_browser = obj_playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                .setArgs(Arrays.asList("--start-maximized")));
        obj_context = obj_browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        obj_Page = obj_context.newPage();
    }
    @Test
    public void validateAddToCart()
    {

        obj_Page.navigate("https://www.saucedemo.com");

        obj_Page.waitForTimeout(2000);
        Locator obj_UsernameLocator = obj_Page.locator(id_username);
        obj_UsernameLocator.fill(username);

        obj_Page.locator(id_password).fill(password);
        obj_Page.locator(css_loginButton).click();
        obj_Page.waitForLoadState();

        Locator obj_addTshirt = obj_Page.locator(tshirt);
        assertThat(obj_addTshirt).isEnabled();
        obj_Page.locator(tshirt).click();
        obj_Page.locator(backpack).click();
        obj_Page.locator(fleeceJacket).click();

        Locator cartBtnLocator = obj_Page.locator(cartBtn);
        assertThat(cartBtnLocator).isEnabled();

        cartBtnLocator.click();
        obj_Page.waitForLoadState();

        Locator cart_container =obj_Page.locator(cartContainer);
        assertThat(cart_container).isVisible();
    }


    @AfterMethod
    public void teardown()
    {
        obj_Page.close();
    }
}
