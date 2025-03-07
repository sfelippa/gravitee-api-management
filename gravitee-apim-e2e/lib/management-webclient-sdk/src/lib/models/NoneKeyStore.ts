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
    KeyStore,
    KeyStoreFromJSON,
    KeyStoreFromJSONTyped,
    KeyStoreToJSON,
} from './';

/**
 * 
 * @export
 * @interface NoneKeyStore
 */
export interface NoneKeyStore extends KeyStore {
}

export function NoneKeyStoreFromJSON(json: any): NoneKeyStore {
    return NoneKeyStoreFromJSONTyped(json, false);
}

export function NoneKeyStoreFromJSONTyped(json: any, ignoreDiscriminator: boolean): NoneKeyStore {
    return json;
}

export function NoneKeyStoreToJSON(value?: NoneKeyStore | null): any {
    return value;
}



