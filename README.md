# sftp-uploader

To upload files to your servers via sftp with multi-thread.

## Usage

1. run `git clone https://github.com/ryoii/sftp-uploader.git`
2. `cd` to the directory of this project
3. run `gradlew clean build shadow:shadowJar`
4 `cd` to ./build/libs
5. run `java -jar sftp-uploader.jar -t ../../base.yml`


## Config

```yaml
nodes:
  - name: node1
    username: username1
    password: password1
    host: host1
    port: 22 
    origin: origin1
    dest: dest1
    rename: rename1

  - name: node2
    username: your server username
    password: your server password
    host: your server host
    port: ssh port
    origin: the file will be upload
    dest: the destination path
    rename: (optional) the file will be rename
```

## Thanks

+ jsch: The sftp client for java.
+ snakeyaml: Yaml parser for java. 