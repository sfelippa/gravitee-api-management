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
/**
 * The list of API resources used by policies like cache resources or oauth2
 * @export
 * @interface Resource
 */
export interface Resource {
    /**
     * 
     * @type {any}
     * @memberof Resource
     */
    configuration: any;
    /**
     * 
     * @type {boolean}
     * @memberof Resource
     */
    enabled?: boolean;
    /**
     * 
     * @type {string}
     * @memberof Resource
     */
    name: string;
    /**
     * 
     * @type {string}
     * @memberof Resource
     */
    type: string;
}

export function ResourceFromJSON(json: any): Resource {
    return ResourceFromJSONTyped(json, false);
}

export function ResourceFromJSONTyped(json: any, ignoreDiscriminator: boolean): Resource {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'configuration': json['configuration'],
        'enabled': !exists(json, 'enabled') ? undefined : json['enabled'],
        'name': json['name'],
        'type': json['type'],
    };
}

export function ResourceToJSON(value?: Resource | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'configuration': value.configuration,
        'enabled': value.enabled,
        'name': value.name,
        'type': value.type,
    };
}


