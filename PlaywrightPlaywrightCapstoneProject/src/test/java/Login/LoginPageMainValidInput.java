package Login;

import com.microsoft.playwright.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPageMainValidInput {
    static String username = "standard_user";
    static String password ="secret_sauce";

    static String css_loginButton="input[type='submit'][value='Login']";
    static String id_username ="#user-name";
    static String id_password = "#password";

    public static void main(String[] args) {
        try(Playwright obj_playwright = Playwright.create())
        {
            Browser obj_browser = obj_playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext obj_context = obj_browser.newContext();
            Page obj_Page = obj_context.newPage();
            obj_Page.navigate("https://www.saucedemo.com");

            obj_Page.waitForTimeout(2000);
            Locator obj_UsernameLocator = obj_Page.locator(id_username);
            obj_UsernameLocator.fill(username);

            obj_Page.locator(id_password).fill(password);
            obj_Page.locator(css_loginButton).click();
            obj_Page.waitForLoadState();

            if(obj_Page.url().contains("inventory.html"))
                System.out.println("PASS : Successfully logged in!");
            else
                System.out.println("FAIL : Invalid credentials!");

            obj_Page.close();

        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
