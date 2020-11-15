all: build

build:
	@docker build -t harbor.gate.sh/bruinkool/garzweiler .

run:
	@docker run --rm -it --net host harbor.gate.sh/bruinkool/garzweiler

publish:
	@docker push harbor.gate.sh/bruinkool/garzweiler

.PHONY: help run
