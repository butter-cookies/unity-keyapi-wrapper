#import "LUKeychainAccess.h"

@interface UniKeyModule : NSObject
/**
 generate singleton instance.
 */
+ (UniKeyModule*) sharedInstance;
/**
 remove all data from keychain.
 */
- (void) deleteAll;
/**
 remove one data from keychain
 */
- (void) deleteKey:(NSString*)key;
/**
 get data from keychain
 */
- (NSString*) getString:(NSString *)key;
/**
 register or update data to keychain.
 */
- (void) setString:(NSString *)key value:(NSString *)value;
@end


@interface UniKeyModule()<LUKeychainErrorHandler>
@property (nonatomic, strong) LUKeychainAccess *keychainAccesses;
/**
 ErrorCode
 
 If it occurred error while using keychain api,
 detailed errors reason will be recorded to this memory area.
 */
@property (nonatomic, strong) NSError* error;
@end


@implementation UniKeyModule
+ (UniKeyModule *) sharedInstance {
    static dispatch_once_t once;
    static UniKeyModule *instance;
    dispatch_once(&once, ^  { instance = [[UniKeyModule alloc] init]; });
    return instance;
}

- (void)keychainAccess:(LUKeychainAccess *)keychainAccess receivedError:(NSError *)error {
    self.error = error;
}

- (id)init {
    self = [super init];
    if (!self) return nil;
    _keychainAccesses = [LUKeychainAccess standardKeychainAccess];
    _keychainAccesses.errorHandler = self;
    return self;
}

- (void)deleteAll {
    self.error = nil;
    [self.keychainAccesses deleteAll];
}

- (void) deleteKey:(NSString*)key {
    self.error = nil;
    [self.keychainAccesses deleteObjectForKey:key];
}

- (NSString*) getString:(NSString *)key {
    self.error = nil;
    return [self.keychainAccesses stringForKey:key];
}

- (void) setString:(NSString *)key value:(NSString *)value {
    self.error = nil;
    [self.keychainAccesses setString:value forKey:key];
}
@end

struct UniKeyModuleString {
    char* value;
    int errorCode;
};

struct UniKeyModuleBoolean {
    bool value;
    int errorCode;
};

extern "C" {
    /**
     remove all data in keychain.
     */
    int deleteAll();
    /**
     remove one data in keychain.
     */
    int deleteKey(const char* key);
    /**
     get data from keychain.
     */
    UniKeyModuleString getString(const char* key);
    /**
     register or update data to keychain.
     */
    int setString(const char* key, const char* value);
    /**
     confirm that recieved key exists in keychain.
     */
    UniKeyModuleBoolean hasKey(const char* key);
}

#define toString(str) [NSString stringWithUTF8String: str ? str : ""]
#define isNullorEmpty(str) ((str == nil) || [(str) isEqualToString:@""])

int deleteAll() {
    UniKeyModule* instance = [UniKeyModule sharedInstance];
    [instance deleteAll];
    if (!instance.error) {
        return 0;
    } else {
        return (int)instance.error.code;
    }
}

int deleteKey(const char* key) {
    UniKeyModule* instance = [UniKeyModule sharedInstance];
    [instance deleteKey:toString(key)];
    if (!instance.error) {
        return 0;
    } else {
        return (int)instance.error.code;
    }
}

UniKeyModuleString getString(const char* key) {
    UniKeyModule* instance = [UniKeyModule sharedInstance];
    NSString* value = [instance getString:toString(key)];
    UniKeyModuleString data;
    data.value = (((value) == nil) ? NULL : strdup([(value) UTF8String]));
    if (!instance.error) {
        data.errorCode = 0;
    } else {
        data.errorCode = (int)instance.error.code;
    }
    return data;
}

int setString(const char* key, const char* value) {
    UniKeyModule* instance = [UniKeyModule sharedInstance];
    [instance setString:toString(key) value:toString(value)];
    if (!instance.error) {
        return 0;
    } else {
        return (int)instance.error.code;
    }
}

UniKeyModuleBoolean hasKey(const char* key) {
    UniKeyModule* instance = [UniKeyModule sharedInstance];
    UniKeyModuleBoolean data;
    data.value = isNullorEmpty([instance getString:toString(key)]);
    if (!instance.error) {
        data.errorCode = 0;
    } else {
        data.errorCode = (int)instance.error.code;
    }
    return data;
}
