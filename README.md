README
======

In order to make life easier I suggest forking this repository into your own github account.
Submission is as simple as committing all your work to github and telling us about your github URI.

THE DATA
--------
Assume all prices are in dollars.

There are 2 csv files included in this task, prices.csv and trades.csv

In prices.csv you will find 3 columns. The format is as follows:

1. timestamp - the time at which the price change occurred
2. bid price - the bid price on the exchange
3. ask price - the asking price on the exchange

In trades.csv you will find 4 columns. The format is as follows:

1. identifier - unique id for this trade
2. trade type - buy or sell
3. opening price - the price the trade was opened at
4. margin (the movement in price that is acceptable before a trade should be liquidated).


THE TASK
--------

To create a liquidation system that accepts the price data, feeds it in, and liquidates any of the trades from the trade data that have exceed their stop margin. The stop price for a Buy is open price - margin, .e.g 700 - 20 = 680 stop price.

The output should be a list of the trades that were liquidated, in the order they were liquidated, and the timestamp and price they were liquidated against.

The finished task should be runnable out of the box. And should be tested. How you choose to implement the rest if entirely up to you. All source code should be supplied so that evaluation can be completed on code quality.}