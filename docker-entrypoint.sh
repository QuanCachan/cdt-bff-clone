#!/bin/sh
set -e

# Additional configuration process

# Parse env variables for secrets
# If /run/secrets/application-xxx.yml (nothing to do, overload by springboot additional-location)
# If /run/secrets/application.env (with content key=value per line) => export key(s)=value(s)
# If /run/secrets/filename (with content as value) => export filename=content_value
SECRETS_DIR="/run/secrets"

if [ -d $SECRETS_DIR ]; then
  for filename in $SECRETS_DIR/*; do
    extension=$(echo $filename | awk -F . '{if (NF>1) {print $NF}}')
    #echo $filename $extension
    # .extension filename content is multiple key=value
    HAS_EXTENSION=$extension
    #echo "HAS_EXTENSION="$HAS_EXTENSION
    if [ -f $filename ]; then
      if [ $HAS_EXTENSION ]; then
        #echo "HAS_EXTENSION"
        if [ $extension == "env" ]; then
          while IFS="=" read -r key value; do
            if [ $key ]; then
              #echo "$key=$value"
              export $key=$value
            fi
          done <"$filename"
        fi
      else
        # filename is key / content is value
        #echo "HAS_NO_EXTENSION"
        filekey=$(basename "$filename")
        export $filekey=$(cat $filename)
      fi
    fi
  done
fi

# Execute Docker CMD
exec "$@"
