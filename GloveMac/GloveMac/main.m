//
//  main.m
//  GloveMac
//
//  Created by Motohiro Takayama on 6/1/13.
//  Copyright (c) 2013 mootoh.net. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Thrower.h"

int main(int argc, const char * argv[])
{
    @autoreleasepool {
        Thrower *thrower = [[Thrower alloc] init];
        [thrower throw];

        NSLog(@"Hello, World!");
    }
    return 0;
}

