# sftp-uploader

To upload files to your servers via sftp with multi-thread.

## Config

```yaml
# nodes:
#   - name: node1
#     username: username1
#     password: password1
#     host: host1
#     port: 22 
#     origin: origin1
#     dest: dest1
#     rename: rename1
#
#   - name: node2
#     username: your server username
#     password: your server password
#     host: your server host
#     port: ssh port
#     origin: the file will be upload
#     dest: the destination path
#     rename: (optional) the file will be rename
```

## Thanks

+ jsch: The sftp client for java.
+ snakeyaml: Yaml parser for java. 