package TestNGSauceDemoWithPlayWright;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestNGDashboard {
    Playwright obj_playwright;
    Browser obj_browser;
    BrowserContext obj_context;
    Page obj_Page;

    static String username = "standard_user";
    static String password ="secret_sauce";
    static String css_loginButton="input[type='submit'][value='Login']";
    static String id_username ="#user-name";
    static String id_password = "#password";
    static String inventory_container = "[data-test='inventory-container']";
    static String filterDropdown ="[data-test='product-sort-container']";
    static String filterAtoZ = "Name (A to Z)";
    static String itemInList = "[data-test='inventory-item']";

    @BeforeMethod
    public void setUp(){
        obj_playwright = Playwright.create();
        obj_browser = obj_playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                .setArgs(Arrays.asList("--start-maximized")));
        obj_context = obj_browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        obj_Page = obj_context.newPage();
    }
    @Test
    public void validateDashBoard()
    {
        obj_Page.navigate("https://www.saucedemo.com");
        obj_Page.waitForLoadState();

        Locator obj_UsernameLocator = obj_Page.locator(id_username);
        obj_UsernameLocator.fill(username);

        obj_Page.locator(id_password).fill(password);
        obj_Page.locator(css_loginButton).click();
        obj_Page.waitForLoadState();

        assertThat(obj_Page).hasURL("https://www.saucedemo.com/inventory.html");

        Locator inventory_containerLocator =obj_Page.locator(inventory_container);
        assertThat(inventory_containerLocator).isVisible();

        Locator item = obj_Page.locator(itemInList).first();
        assertThat(item).isVisible();

        Locator filterLocator =obj_Page.locator(filterDropdown);
        assertThat(filterLocator).isVisible();
        filterLocator.selectOption(filterAtoZ);
        filterLocator.waitFor();
        String selectedValue =filterLocator.locator("option:checked").textContent();
        System.out.println(selectedValue);
        assertThat(filterLocator).containsText(filterAtoZ);

        obj_Page.close();
    }


    @AfterMethod
    public void teardown()
    {
        obj_Page.close();
    }
}
