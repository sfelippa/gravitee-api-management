/* tslint:disable */
/* eslint-disable */
/**
 * Gravitee.io Portal Rest API
 * API dedicated to the devportal part of Gravitee
 *
 * Contact: contact@graviteesource.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface PageMedia
 */
export interface PageMedia {
    /**
     * the name of the media.
     * @type {string}
     * @memberof PageMedia
     */
    name: string;
    /**
     * link to download the media.
     * @type {string}
     * @memberof PageMedia
     */
    link: string;
    /**
     * type of the media.
     * @type {string}
     * @memberof PageMedia
     */
    type: string;
}

export function PageMediaFromJSON(json: any): PageMedia {
    return PageMediaFromJSONTyped(json, false);
}

export function PageMediaFromJSONTyped(json: any, ignoreDiscriminator: boolean): PageMedia {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'name': json['name'],
        'link': json['link'],
        'type': json['type'],
    };
}

export function PageMediaToJSON(value?: PageMedia | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'name': value.name,
        'link': value.link,
        'type': value.type,
    };
}


