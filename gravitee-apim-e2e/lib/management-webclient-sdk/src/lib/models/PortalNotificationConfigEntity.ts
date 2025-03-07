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
    NotificationConfigType,
    NotificationConfigTypeFromJSON,
    NotificationConfigTypeFromJSONTyped,
    NotificationConfigTypeToJSON,
} from './';

/**
 * 
 * @export
 * @interface PortalNotificationConfigEntity
 */
export interface PortalNotificationConfigEntity {
    /**
     * 
     * @type {NotificationConfigType}
     * @memberof PortalNotificationConfigEntity
     */
    config_type?: NotificationConfigType;
    /**
     * 
     * @type {Array<string>}
     * @memberof PortalNotificationConfigEntity
     */
    hooks?: Array<string>;
    /**
     * 
     * @type {string}
     * @memberof PortalNotificationConfigEntity
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof PortalNotificationConfigEntity
     */
    referenceId?: string;
    /**
     * 
     * @type {string}
     * @memberof PortalNotificationConfigEntity
     */
    referenceType?: string;
    /**
     * 
     * @type {string}
     * @memberof PortalNotificationConfigEntity
     */
    user?: string;
}

export function PortalNotificationConfigEntityFromJSON(json: any): PortalNotificationConfigEntity {
    return PortalNotificationConfigEntityFromJSONTyped(json, false);
}

export function PortalNotificationConfigEntityFromJSONTyped(json: any, ignoreDiscriminator: boolean): PortalNotificationConfigEntity {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'config_type': !exists(json, 'config_type') ? undefined : NotificationConfigTypeFromJSON(json['config_type']),
        'hooks': !exists(json, 'hooks') ? undefined : json['hooks'],
        'name': !exists(json, 'name') ? undefined : json['name'],
        'referenceId': !exists(json, 'referenceId') ? undefined : json['referenceId'],
        'referenceType': !exists(json, 'referenceType') ? undefined : json['referenceType'],
        'user': !exists(json, 'user') ? undefined : json['user'],
    };
}

export function PortalNotificationConfigEntityToJSON(value?: PortalNotificationConfigEntity | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'config_type': NotificationConfigTypeToJSON(value.config_type),
        'hooks': value.hooks,
        'name': value.name,
        'referenceId': value.referenceId,
        'referenceType': value.referenceType,
        'user': value.user,
    };
}


