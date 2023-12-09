# Mobile app automation test using Appium

## Appium server

### Windows

1. Download and Install latest [node and npm tools](https://nodejs.org/en/) MSI (version 6.10.0 LTS)
2. Set npm mirror
```
$ npm config set registry http://registry.cnpmjs.org
```

3. Install Appium server
```
$ npm install -g appium 
```

### Mac
1. Install node
```
$ brew install node
```

2. Set npm mirror
```
$ npm config set registry http://registry.cnpmjs.org
```

or using cnpm
```
npm install -g cnpm --registry=https://registry.npm.taobao.org
```

3. Install Appium server
```
$ npm install -g appium
```

or using cnpm
```
$ cnpm install -g appium
```

4. update Appium server
```
$ cnpm -g install npm@latest
$ cnpm -g install appiumm@latest
```

update appium with latest dependency packages
(https://github.com/appium/appium/issues/7524)
```
$ cnpm uninstall -g appium && cnpm install -g appium --no-shrinkwrap
```


## Client

* pre-requirement: Java 8

* Java-client

## Mobile device support
* iOS: 7.1 and up
* Android: 4.2 and up


## Run test against Mobile App
1. Drop an apk / ipa / zip into project ${project_basedir}/apps folder, may need ```$ mkdir apps``` first. Please note, there must be only one app in this folder.
2. Create a ${project_basedir}/log dir, ```$ mkdir log```
3. Create a ${project_basedir}/screenshot dir, ```$ mkdir screenshot```
3. Running in IntelliJ
4. Running test in command line using maven

```
$ mvn test -Pfunctional-test,android
$ mvn test -Pfunctional-test,ios
```



## Device
* Android: must disable animations in Developer Options