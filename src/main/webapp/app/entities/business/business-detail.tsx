import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './business.reducer';
import { IBusiness } from 'app/shared/model/business.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBusinessDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BusinessDetail extends React.Component<IBusinessDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { businessEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.business.detail.title">Business</Translate> [<b>{businessEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="businessName">
                <Translate contentKey="accounTalkApp.business.businessName">Business Name</Translate>
              </span>
            </dt>
            <dd>{businessEntity.businessName}</dd>
            <dt>
              <span id="businessDescription">
                <Translate contentKey="accounTalkApp.business.businessDescription">Business Description</Translate>
              </span>
            </dt>
            <dd>{businessEntity.businessDescription}</dd>
            <dt>
              <span id="businessType">
                <Translate contentKey="accounTalkApp.business.businessType">Business Type</Translate>
              </span>
            </dt>
            <dd>{businessEntity.businessType}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.business.client">Client</Translate>
            </dt>
            <dd>{businessEntity.client ? businessEntity.client.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/business" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/business/${businessEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ business }: IRootState) => ({
  businessEntity: business.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BusinessDetail);
