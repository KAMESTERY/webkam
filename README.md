# webkam
Kamestery Web Application

## Setup

```{bash}

# Install Go and Clojure
brew install go clojure

## Webpack

```{bash}
# Install Dependencies
npm i
# Build Assets
npm run build

```

```

## Clojurescript

```{bash}

# Get some help
clj --main cljs.main --help

# Get a REPL going
clj --main cljs.main --compile hello-world.core --repl

# Production Build
clj -m cljs.main -O advanced -o static/js/ui.js -c hello-world.core

```

## Build Project

```{bash}

# Clone Repository
git clone https://github.com/KAMESTERY/webkam.git

# Get inside project directory
cd /path/to/webkam

# Resolve dependencies
go mod vendor

# Build project
go build

# Run project
./kamestery.com

```

## Environments

* [dev](https://dev.kamestery.com)
* [stage](https://stage.kamestery.com)
* [prod](https://kamestery.com)


## Miscellaneous

[Figwheel with new Clojure 1.9 CLI tools](http://www.functionalbytes.nl/clojure/nodejs/figwheel/repl/clojurescript/cli/2017/12/20/tools-deps-figwheel.html)

