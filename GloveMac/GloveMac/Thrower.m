//
//  Thrower.m
//  GloveMac
//
//  Created by Motohiro Takayama on 6/1/13.
//  Copyright (c) 2013 mootoh.net. All rights reserved.
//

#import "Thrower.h"
#import <Cocoa/Cocoa.h>

@interface Thrower ()
- (NSString *) getClipboardData;
- (void) broadcast:(NSString *)message;
@end

@implementation Thrower

- (NSString *) getClipboardData
{
    NSPasteboard *pasteboard = [NSPasteboard generalPasteboard];
    NSArray *classes = [[NSArray alloc] initWithObjects:[NSString class], nil];
    NSArray *copiedItems = [pasteboard readObjectsForClasses:classes options:nil];
    for (NSString *item in copiedItems)
        return item;
    return nil;
}

- (void) broadcast:(NSString *)message
{
    // convert to JSON
    NSDictionary *dict = @{@"text": message};
    NSError *error = nil;
    NSData *json = [NSJSONSerialization dataWithJSONObject:dict options:0 error:&error];
    if (error) {
        NSLog(@"error in converting to JSON: %@", [error localizedDescription]);
        return;
    }

    // send it to server
    NSURL *url = [NSURL URLWithString:@"https://api.parse.com/1/functions/throw"];
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:url];
    [request setHTTPMethod:@"POST"];

    NSDictionary *headers = @{
                              @"X-Parse-Application-Id": @"V9lZJMbU5W9H5JS3lxO9dfFn9kHEmb9Y7mjtixLM",
                              @"X-Parse-REST-API-Key": @"bL2r68vwDX6oa3pT0PaCsh2qxvNJkfNobWnqL407",
                              @"Content-Type": @"application/json"
                              };
    [request setAllHTTPHeaderFields:headers];
    [request setHTTPBody:json];

    NSURLResponse *response = nil;
    NSData *returned = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    if (error) {
        NSLog(@"error in sending JSON to server: %@", [error localizedDescription]);
        return;
    }
    NSLog(@"sent request: %@", [[NSString alloc] initWithData:returned encoding:NSUTF8StringEncoding]);
}

- (void) throw
{
    NSString *clipped = [self getClipboardData];
    [self broadcast:clipped];
}

@end
