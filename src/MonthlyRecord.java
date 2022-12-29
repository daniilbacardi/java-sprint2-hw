class MonthlyRecord {
    final String ITEM_NAME;
    final boolean IS_EXPENSE;
    final int QUANTITY;
    final int SUM_OF_ONE;

    MonthlyRecord(String ITEM_NAME, boolean IS_EXPENSE, int QUANTITY, int SUM_OF_ONE) {
        this.ITEM_NAME = ITEM_NAME;
        this.IS_EXPENSE = IS_EXPENSE;
        this.QUANTITY = QUANTITY;
        this.SUM_OF_ONE = SUM_OF_ONE;
    }
}