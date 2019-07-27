import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './benefits.reducer';
import { IBenefits } from 'app/shared/model/benefits.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBenefitsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BenefitsDetail extends React.Component<IBenefitsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { benefitsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.benefits.detail.title">Benefits</Translate> [<b>{benefitsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="benefitType">
                <Translate contentKey="accounTalkApp.benefits.benefitType">Benefit Type</Translate>
              </span>
            </dt>
            <dd>{benefitsEntity.benefitType}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="accounTalkApp.benefits.description">Description</Translate>
              </span>
            </dt>
            <dd>{benefitsEntity.description}</dd>
            <dt>
              <span id="value">
                <Translate contentKey="accounTalkApp.benefits.value">Value</Translate>
              </span>
            </dt>
            <dd>{benefitsEntity.value}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.benefits.employmentDetails">Employment Details</Translate>
            </dt>
            <dd>{benefitsEntity.employmentDetails ? benefitsEntity.employmentDetails.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/benefits" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/benefits/${benefitsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ benefits }: IRootState) => ({
  benefitsEntity: benefits.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BenefitsDetail);
