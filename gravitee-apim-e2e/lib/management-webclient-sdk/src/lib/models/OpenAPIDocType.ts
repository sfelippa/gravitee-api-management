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
    Enabled,
    EnabledFromJSON,
    EnabledFromJSONTyped,
    EnabledToJSON,
} from './';

/**
 * 
 * @export
 * @interface OpenAPIDocType
 */
export interface OpenAPIDocType {
    /**
     * 
     * @type {string}
     * @memberof OpenAPIDocType
     */
    defaultType?: string;
    /**
     * 
     * @type {Enabled}
     * @memberof OpenAPIDocType
     */
    redoc?: Enabled;
    /**
     * 
     * @type {Enabled}
     * @memberof OpenAPIDocType
     */
    swagger?: Enabled;
}

export function OpenAPIDocTypeFromJSON(json: any): OpenAPIDocType {
    return OpenAPIDocTypeFromJSONTyped(json, false);
}

export function OpenAPIDocTypeFromJSONTyped(json: any, ignoreDiscriminator: boolean): OpenAPIDocType {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'defaultType': !exists(json, 'defaultType') ? undefined : json['defaultType'],
        'redoc': !exists(json, 'redoc') ? undefined : EnabledFromJSON(json['redoc']),
        'swagger': !exists(json, 'swagger') ? undefined : EnabledFromJSON(json['swagger']),
    };
}

export function OpenAPIDocTypeToJSON(value?: OpenAPIDocType | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'defaultType': value.defaultType,
        'redoc': EnabledToJSON(value.redoc),
        'swagger': EnabledToJSON(value.swagger),
    };
}


