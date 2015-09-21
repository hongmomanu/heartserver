# heartserver

FIXME

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run

## License

Copyright © 2015 FIXME


接口
(room:1,2,3,4,heartbig)
(status:0,1,2,3)0：呼叫,1：检查,2：完成,3：过号
http://localhost:3000/fireprop?room=heartbig&name=tip&value=温馨提示：不以物喜，不以己悲。居庙堂之高则忧其民；处江湖之远则忧其君。
http://localhost:3000/callToRoom?room=1&lineno=A001&name=jack&status=0
http://localhost:3000/fireprop?room=heartbig&name=showlines&value=4




