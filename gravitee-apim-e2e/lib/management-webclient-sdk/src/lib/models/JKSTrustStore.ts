/* tslint:disable */
/* eslint-disable */
/**
 * Gravitee.io - Management API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
import {
    JKSKeyStoreAllOf,
    JKSKeyStoreAllOfFromJSON,
    JKSKeyStoreAllOfFromJSONTyped,
    JKSKeyStoreAllOfToJSON,
    TrustStore,
    TrustStoreFromJSON,
    TrustStoreFromJSONTyped,
    TrustStoreToJSON,
} from './';

/**
 * 
 * @export
 * @interface JKSTrustStore
 */
export interface JKSTrustStore extends TrustStore {
    /**
     * 
     * @type {string}
     * @memberof JKSTrustStore
     */
    path?: string;
    /**
     * 
     * @type {string}
     * @memberof JKSTrustStore
     */
    content?: string;
    /**
     * 
     * @type {string}
     * @memberof JKSTrustStore
     */
    password?: string;
}

export function JKSTrustStoreFromJSON(json: any): JKSTrustStore {
    return JKSTrustStoreFromJSONTyped(json, false);
}

export function JKSTrustStoreFromJSONTyped(json: any, ignoreDiscriminator: boolean): JKSTrustStore {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        ...TrustStoreFromJSONTyped(json, ignoreDiscriminator),
        'path': !exists(json, 'path') ? undefined : json['path'],
        'content': !exists(json, 'content') ? undefined : json['content'],
        'password': !exists(json, 'password') ? undefined : json['password'],
    };
}

export function JKSTrustStoreToJSON(value?: JKSTrustStore | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        ...TrustStoreToJSON(value),
        'path': value.path,
        'content': value.content,
        'password': value.password,
    };
}



