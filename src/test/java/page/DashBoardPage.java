package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashBoardPage {
    private final SelenideElement header = $("[data-test-id='dashboard']");
    ElementsCollection cards = $$(".list__item div");


    public DashBoardPage() {
        header.shouldBe(Condition.visible);
    }

    // получение баланса карты по id
    public int showCardBalance(DataHelper.CardInfo cardInfo) {
        SelenideElement card = cards.findBy(attribute("data-test-id", cardInfo.getCardId()));
        String text = card.getText();
        return extractBalance(text);
    }

    // вспомогательный метод, чтобы "вытащить" баланс
    private int extractBalance(String text) {
        String prefix = "баланс:";
        int beginIndex = text.indexOf(prefix);
        String postfix = "р.";
        int endIndex = text.indexOf(postfix);
        String balancePart = text.substring(beginIndex + prefix.length(), endIndex).trim();
        return Integer.parseInt(balancePart);
    }

    // выбор карты для пополнения
    public TransferCardPage transferCard(DataHelper.CardInfo cardInfo) {
        cards.findBy(attribute("data-test-id", cardInfo.getCardId()))
                .$("button").click();
        return new TransferCardPage();

    }
}
