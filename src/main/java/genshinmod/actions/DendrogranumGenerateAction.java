package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import genshinmod.cards.Dendrogranum;

public class DendrogranumGenerateAction extends AbstractGameAction {
    private int elementlayers;

    public DendrogranumGenerateAction(int elementlayers) {
        this.elementlayers = elementlayers;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.elementlayers != -1) {
            effect = this.elementlayers;
        }

        // 创建 Dendrogranum 实例并设置其属性
        Dendrogranum c = new Dendrogranum();
        c.setX(effect); // 假设 Dendrogranum 类有一个 setEffect 方法

        System.out.println("Generating Dendrogranum with effect: " + effect);
        System.out.println("Dendrogranum damage: " + c.damage);
        System.out.println("Dendrogranum magicNumber: " + c.magicNumber);

        // 创建并使用 AddCardToHandAction 将 Dendrogranum 添加到手牌中
        AbstractCard card = c; // 将 Dendrogranum 转换为 AbstractCard 类型
        this.addToBot(new AddCardToHandAction(card));

        this.isDone = true;
    }
}
