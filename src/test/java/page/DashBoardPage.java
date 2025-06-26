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
        var text = card.getText();
        return extractBalance(text);
    }

    // вспомогательный метод, чтобы "вытащить" баланс
    private int extractBalance(String text) {
        var prefix = "баланс:";
        var beginIndex = text.indexOf(prefix);
        var postfix = "р.";
        var endIndex = text.indexOf(postfix);
        var balancePart = text.substring(beginIndex + prefix.length(), endIndex).trim();
        return Integer.parseInt(balancePart);
    }

    // выбор карты для пополнения
    public TransferCardPage transferCard(DataHelper.CardInfo cardInfo) {
        cards.findBy(attribute("data-test-id", cardInfo.getCardId()))
                .$("button").click();
        return new TransferCardPage();

    }

    //проверка баланса после перевода
    public void showCardBalanceAfterTransfer(DataHelper.CardInfo cardInfo, int expectedBalanceAfterTransfer ) {
        SelenideElement card = cards.findBy(attribute("data-test-id", cardInfo.getCardId()));
        var textExpectedBalance = String.valueOf(expectedBalanceAfterTransfer);
        card.shouldHave(Condition.text(textExpectedBalance));
    }
}
