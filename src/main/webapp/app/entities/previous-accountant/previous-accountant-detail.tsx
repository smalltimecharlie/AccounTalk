import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './previous-accountant.reducer';
import { IPreviousAccountant } from 'app/shared/model/previous-accountant.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPreviousAccountantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PreviousAccountantDetail extends React.Component<IPreviousAccountantDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { previousAccountantEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.previousAccountant.detail.title">PreviousAccountant</Translate> [
            <b>{previousAccountantEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="accounTalkApp.previousAccountant.name">Name</Translate>
              </span>
            </dt>
            <dd>{previousAccountantEntity.name}</dd>
            <dt>
              <span id="email">
                <Translate contentKey="accounTalkApp.previousAccountant.email">Email</Translate>
              </span>
            </dt>
            <dd>{previousAccountantEntity.email}</dd>
            <dt>
              <span id="phoneNumber">
                <Translate contentKey="accounTalkApp.previousAccountant.phoneNumber">Phone Number</Translate>
              </span>
            </dt>
            <dd>{previousAccountantEntity.phoneNumber}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.previousAccountant.client">Client</Translate>
            </dt>
            <dd>{previousAccountantEntity.client ? previousAccountantEntity.client.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/previous-accountant" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/previous-accountant/${previousAccountantEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ previousAccountant }: IRootState) => ({
  previousAccountantEntity: previousAccountant.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PreviousAccountantDetail);
