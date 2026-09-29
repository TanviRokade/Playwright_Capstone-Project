package TestNGSauceDemoWithPlayWright;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.AssertJUnit.assertFalse;

public class TestNGLogin {
    Playwright obj_playwright;
    Browser obj_browser;
    BrowserContext obj_context;
    Page obj_Page;

    static String username = "standard_user";
    static String password ="secret_sauce";
    static String css_loginButton="input[type='submit'][value='Login']";
    static String id_username ="#user-name";
    static String id_password = "#password";

    @BeforeMethod
    public void setUp(){
        obj_playwright = Playwright.create();
        obj_browser = obj_playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                .setArgs(Arrays.asList("--start-maximized")));
        obj_context = obj_browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        obj_Page = obj_context.newPage();
    }
    @Test
    public void validateValidSauceDemo()
    {
        obj_Page.navigate("https://www.saucedemo.com");
        obj_Page.waitForLoadState();

        Locator obj_UsernameLocator = obj_Page.locator(id_username);
        obj_UsernameLocator.fill(username);

        obj_Page.locator(id_password).fill(password);
        obj_Page.locator(css_loginButton).click();
        obj_Page.waitForLoadState();

        assertThat(obj_Page).hasURL("https://www.saucedemo.com/inventory.html");
    }
    @Test
    public void validateInValidSauceDemo()
    {
        obj_Page.navigate("https://www.saucedemo.com");
        obj_Page.waitForLoadState();

        Locator obj_UsernameLocator = obj_Page.locator(id_username);
        obj_UsernameLocator.fill(username);

        obj_Page.locator(id_password).fill(password);
        obj_Page.locator(css_loginButton).click();
        obj_Page.waitForLoadState();

        assertFalse(obj_Page.url().contains("https://www.saucedemo.com/inventory1.html"));
    }
    @AfterMethod
    public void teardown()
    {
        obj_Page.close();
    }
}
