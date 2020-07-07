package engine;

import java.util.Random;

public class SymbolManager {

    private static SymbolManager instance;
    public static SymbolManager getInstance(){
        if(instance==null)instance = new SymbolManager();
        return instance;
    }

    private  SymbolManagerRefresh refresh;
    private  Character symbol;

    public  Character getActualSymbol(){
        if(refresh==null||refresh.isInterrupted()){
            refresh = new SymbolManagerRefresh();
            refresh.start();
        }
        return symbol;
    }

    private static Character newSymbol(){
        return Symbols.values()[new Random().nextInt(Symbols.values().length)].getCharacter();
    }

    public class SymbolManagerRefresh extends Thread{

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                try{
                    symbol = newSymbol();
                    Thread.sleep(Prop.SYMBOL_CHANGE_TIME);
                }catch (Exception e){
                    getActualSymbol();
                }
            }
        }
    }
    public enum Symbols {

        A('A'),
        a('a'),
        B('B'),
        b('b'),
        C('C'),
        c('c'),
        D('D'),
        d('d'),
        E('E'),
        e('e'),
        F('F'),
        f('f'),
        G('G'),
        g('g'),
        H('H'),
        h('h'),
        I('I'),
        i('i'),
        J('J'),
        j('j'),
        K('K'),
        k('k'),
        L('L'),
        l('l'),
        M('M'),
        m('m'),
        N('N'),
        n('n'),
        O('O'),
        o('o'),
        P('P'),
        p('p'),
        Q('Q'),
        q('q'),
        R('R'),
        r('r'),
        S('S'),
        s('s'),
        T('T'),
        t('t'),
        U('U'),
        u('u'),
        V('V'),
        v('v'),
        W('W'),
        w('w'),
        X('X'),
        x('x'),
        Y('Y'),
        y('y'),
        Z('Z'),
        z('z'),
        number_1('1'),
        number_2('2'),
        number_3('3'),
        number_4('4'),
        number_5('5'),
        number_6('6'),
        number_7('7'),
        number_8('8'),
        number_9('9'),
        number_0('0');

        private Character character;
        public Character getCharacter(){
            return character;
        }
        Symbols(Character character){
            this.character = character;
        }
    }
}
