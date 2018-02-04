The application usees Retrofit library to get the data. The Application class is used as  the holder
of application status-thus orientation changes will not cause network call .
 Application class Sunbank acts as a singleton holding the fetched data as well.
  Thus getTransListFromJson is invoked the first time.
App is launched and since then getTransListFromJson   only invoked only when the holder is empty; isValid() method ensures data parsing is correct and
triggers a total calculation if that is the case.

The TransactionAdpter class is responsible for sorting the data before binding to recycle viewholder.

TransactionAdapter also attaches the listener to display the transaction details dialog when a transaction item clicked.
It make sure the transaction list are displayed with blue color for credit amount and red for withdrawal amounts.
The total also come in blue or red depending on the total value.




Both unit and androidTest classes are included ,more as a place holder
to put further tests.