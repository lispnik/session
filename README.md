# SESSION

Session is a live-coding environment, based on Clojure, Om, and Datomic. You can think of it as a web-based REPL, backed by a database.

Check out the [video](https://vimeo.com/89899023) and [introductory blog post.](https://medium.com/p/1a12997a5f70)

Join the [mailing list.](https://groups.google.com/forum/#!forum/session-platform)

# Usage

Make you sure have Leiningen installed. 

Clone the repo, and cd into it:

    git clone https://github.com/kovasb/session.git
    cd session

Launch session with the default port 8080:

    lein run

Launch session with a custom port:

    lein run "{:web-port 8090}"

Use Chrome to navigate to the port on localhost.

Check out the example session for the kinds of operations currently supported.

## Paredit

Session's editor supports the following Paredit actions:

| Action        | Binding           |
| ------------- |-------------| 
| Open Round      | ( | 
| Close Round     | )      | 
| Close Round & Newline | Alt-Enter      |   
| Open Square | [ |
| Close Square | ] |
| Open Curly | { |
| Close Curly | } |
| Double Quote | " |
| Backwards Delete | Backspace |
| | Delete |
| Kill | Ctrl-K |
| Wrap Round | Alt-( |
| Wrap Square | Alt-[ |
| Wrap Curly | Alt-{ |
| Splice | Alt-S |
| Raise | Ctrl-R |
| Split | Shift-Alt-S | 
| Join | Alt-J |
| Forward Slurp | Ctrl-) |
| | Ctrl-Right |
| Forward Barf | Ctrl-} |
| | Ctrl-Left |
| Backward Slurp | Ctrl-( |
| | Ctrl-Alt-Left |
| | Ctrl-[ |
| Backward Barf | Ctrl-{ |
| | Ctrl-Alt-Right |
| | Ctrl-] |



## Loading libraries

You can dynamically load libraries in Session using [alembic](https://github.com/pallet/alembic).

In a session, require alembic:

    (require 'alembic.still)
    
load the desired artifact (this may take a few moments; you can follow the progress in the terminal):

    (alembic.still/distill '[incanter "1.5.4"])

require a namespace from the artifact:

    (require 'incanter.core)
    
use a function from the namespace:

    (incanter.core/cumulative-sum (range 100))

# Datomic "Gotcha"

Make sure there is no pre-existing Datomic transactor already running when launching Session. If Session fails to run, check to see there is no other datomic transactor running. 

Session tries to manage its own instance of the datomic transactor. When you close the Session process, this transactor may not be automatically killed. 

Session uses the transactor in a somewhat unorthodox use case, so the hooks provided by datomic are not ideal for managing it in this way. Pull requests welcome to improve the situation.

## License

Session includes Datomic Free Edition, which is governed by [this license](https://github.com/kovasb/session/blob/master/vendor/datomic-free-0.9.4556/LICENSE)

Session itself is Copyright (C) 2014 Kovas Boguta

Distributed under the Eclipse Public License, the same as Clojure.
