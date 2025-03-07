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
    MetadataFormat,
    MetadataFormatFromJSON,
    MetadataFormatFromJSONTyped,
    MetadataFormatToJSON,
} from './';

/**
 * 
 * @export
 * @interface ApiMetadataEntity
 */
export interface ApiMetadataEntity {
    /**
     * 
     * @type {string}
     * @memberof ApiMetadataEntity
     */
    apiId?: string;
    /**
     * 
     * @type {string}
     * @memberof ApiMetadataEntity
     */
    defaultValue?: string;
    /**
     * 
     * @type {MetadataFormat}
     * @memberof ApiMetadataEntity
     */
    format?: MetadataFormat;
    /**
     * 
     * @type {string}
     * @memberof ApiMetadataEntity
     */
    key: string;
    /**
     * 
     * @type {string}
     * @memberof ApiMetadataEntity
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof ApiMetadataEntity
     */
    value: string;
}

export function ApiMetadataEntityFromJSON(json: any): ApiMetadataEntity {
    return ApiMetadataEntityFromJSONTyped(json, false);
}

export function ApiMetadataEntityFromJSONTyped(json: any, ignoreDiscriminator: boolean): ApiMetadataEntity {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'apiId': !exists(json, 'apiId') ? undefined : json['apiId'],
        'defaultValue': !exists(json, 'defaultValue') ? undefined : json['defaultValue'],
        'format': !exists(json, 'format') ? undefined : MetadataFormatFromJSON(json['format']),
        'key': json['key'],
        'name': !exists(json, 'name') ? undefined : json['name'],
        'value': json['value'],
    };
}

export function ApiMetadataEntityToJSON(value?: ApiMetadataEntity | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'apiId': value.apiId,
        'defaultValue': value.defaultValue,
        'format': MetadataFormatToJSON(value.format),
        'key': value.key,
        'name': value.name,
        'value': value.value,
    };
}


