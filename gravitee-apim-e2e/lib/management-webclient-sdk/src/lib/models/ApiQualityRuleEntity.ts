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
 * 
 * @export
 * @interface ApiQualityRuleEntity
 */
export interface ApiQualityRuleEntity {
    /**
     * 
     * @type {string}
     * @memberof ApiQualityRuleEntity
     */
    api?: string;
    /**
     * 
     * @type {boolean}
     * @memberof ApiQualityRuleEntity
     */
    checked?: boolean;
    /**
     * 
     * @type {Date}
     * @memberof ApiQualityRuleEntity
     */
    created_at?: Date;
    /**
     * 
     * @type {string}
     * @memberof ApiQualityRuleEntity
     */
    quality_rule?: string;
    /**
     * 
     * @type {Date}
     * @memberof ApiQualityRuleEntity
     */
    updated_at?: Date;
}

export function ApiQualityRuleEntityFromJSON(json: any): ApiQualityRuleEntity {
    return ApiQualityRuleEntityFromJSONTyped(json, false);
}

export function ApiQualityRuleEntityFromJSONTyped(json: any, ignoreDiscriminator: boolean): ApiQualityRuleEntity {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'api': !exists(json, 'api') ? undefined : json['api'],
        'checked': !exists(json, 'checked') ? undefined : json['checked'],
        'created_at': !exists(json, 'created_at') ? undefined : (new Date(json['created_at'])),
        'quality_rule': !exists(json, 'quality_rule') ? undefined : json['quality_rule'],
        'updated_at': !exists(json, 'updated_at') ? undefined : (new Date(json['updated_at'])),
    };
}

export function ApiQualityRuleEntityToJSON(value?: ApiQualityRuleEntity | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'api': value.api,
        'checked': value.checked,
        'created_at': value.created_at === undefined ? undefined : (value.created_at.toISOString()),
        'quality_rule': value.quality_rule,
        'updated_at': value.updated_at === undefined ? undefined : (value.updated_at.toISOString()),
    };
}


