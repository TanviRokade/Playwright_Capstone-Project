package AddToCart;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class AddToCartMain {
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

    public static void main(String[] args) {
        try(Playwright obj_playwright = Playwright.create())
        {
            Browser obj_browser = obj_playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
            BrowserContext obj_context = obj_browser.newContext();
            Page obj_Page = obj_context.newPage();
            obj_Page.navigate("https://www.saucedemo.com");

            obj_Page.waitForTimeout(2000);
            Locator obj_UsernameLocator = obj_Page.locator(id_username);
            obj_UsernameLocator.fill(username);

            obj_Page.locator(id_password).fill(password);
            obj_Page.locator(css_loginButton).click();
            obj_Page.waitForLoadState();

            Locator obj_addTshirt = obj_Page.locator(tshirt);
            if(obj_addTshirt.isEnabled())
                System.out.println("PASS : Items 'Add to Cart' button is enabled");
            else
                System.out.println("FAIL : Items 'Add to Cart' button is not enabled");
            obj_Page.locator(tshirt).click();
            obj_Page.locator(backpack).click();
            obj_Page.locator(fleeceJacket).click();

            Locator cartBtnLocator = obj_Page.locator(cartBtn);
            if(cartBtnLocator.isVisible() && cartBtnLocator.isEnabled())
                System.out.println("PASS : Cart button is visible and enabled on dashboard!");
            else
                System.out.println("FAIL : Cart button is not visible and enabled on dashboard!");

            cartBtnLocator.click();
            obj_Page.waitForLoadState();

            Locator cart_container =obj_Page.locator(cartContainer);
            if(cart_container.isVisible())
                System.out.println("PASS : Cart list is visible");
            else
                System.out.println("FAIL : Cart list is not visible");


            obj_Page.close();
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
